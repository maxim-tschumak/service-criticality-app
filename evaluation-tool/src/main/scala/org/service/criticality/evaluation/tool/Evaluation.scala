package org.service.criticality.evaluation.tool

case class Evaluation(surveyResults: SurveyResults, frameworkResults: FrameworkResults, correlation: Correlation.Value) {
  private lazy val surveyServiceRanking = surveyResults.ranking
  private lazy val frameworkServiceRanking = frameworkResults.ranking

  private lazy val metrics = frameworkResults.metrics

  private val surveyServicePoints: Map[String, Double] = surveyResults.pointsPerService
  private val frameworkServicePoints: Map[String, Double] = frameworkResults.pointsPerService

  private lazy val overallCorrelation: Double =
    if (Correlation.Pearson == correlation) Utils.pearson(surveyServicePoints, frameworkServicePoints)
    else Utils.spearman(surveyServiceRanking, frameworkServiceRanking)

  private lazy val metricsCorrelations: Map[String, Double] = {
    require(surveyResults.services == frameworkResults.services)

    val metricsCorrelations = for (
      metric <- metrics;
      metricRankingOrPoints = if (correlation == Correlation.Spearman) Utils.rank(frameworkResults.pointsPerMetric(metric)) else frameworkResults.pointsPerMetric(metric);
      surveyRankingOrPoints = if (correlation == Correlation.Spearman) surveyServiceRanking else surveyServicePoints
    ) yield (metric, Utils.sprearsonman(metricRankingOrPoints, surveyRankingOrPoints))

    metricsCorrelations.toMap
  }

  override def toString: String = {
    val bold = "\033[1m"
    val reset = "\033[0m"

    val builder = new StringBuilder
    builder ++= f"All metrics: $bold$overallCorrelation%.4f$reset\n"
    for ((metricName, metricCorrelation) <- metricsCorrelations) builder ++= f"$metricName: $metricCorrelation%.4f\n"
    builder.toString
  }
}

object Correlation extends Enumeration {
  val Spearman = Value("spearman")
  val Pearson = Value("pearson")
}