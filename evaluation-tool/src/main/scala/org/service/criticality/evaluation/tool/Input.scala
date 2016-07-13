package org.service.criticality.evaluation.tool

case class FrameworkResults(metricValues: Seq[MetricValue]) {
  lazy val services: Set[String] = metricValues.map(_.service).toSet
  lazy val metrics: Set[String] = metricValues.map(_.metric).toSet

  lazy val pointsPerService: Map[String, Double] = metricValues.groupBy(_.service).mapValues(_.map(_.value).sum)

  def without(servicesToExclude: Seq[String]): FrameworkResults =
    FrameworkResults(metricValues.filterNot(servicesToExclude contains _.service))

  lazy val ranking: Map[String, Double] = {
    val servicePoints: Map[String, Double] = metricValues.groupBy(_.service).mapValues(_.map(_.value).sum)
    Utils.rank(servicePoints)
  }

  def pointsPerMetric(metricName: String): Map[String, Double] =
    metricValues.filter(_.metric == metricName).map(x => (x.service, x.value)).toMap

  def normalized: FrameworkResults = FrameworkResults(
    metricValues.groupBy(_.metric).mapValues(normalize(_)).values.flatten.toSeq)

  private def normalize(values: Seq[MetricValue]): Seq[MetricValue] = {
    val min = values.minBy(_.value).value
    val max = values.maxBy(_.value).value

    values.map(metricValue => MetricValue(metricValue.service, metricValue.metric,
      100 * (metricValue.value - min) / (max - min)))
  }
}

case class MetricValue(service: String, metric: String, value: Double)

case class SurveyResults(votes: Seq[Vote]) {
  private lazy val votesPerService: Map[String, Seq[Vote]] = votes.groupBy(_.service)

  lazy val services: Set[String] = votes.map(_.service).toSet
  lazy val pointsPerService: Map[String, Double] = votesPerService.mapValues(_.map(_.points).sum.toDouble)

  lazy val ranking: Map[String, Double] = Utils.rank(pointsPerService)

  def without(servicesToExclude: Seq[String]): SurveyResults =
    SurveyResults(votes.filterNot(servicesToExclude contains _.service))

}


case class Vote(service: String, person: String, points: Int)


