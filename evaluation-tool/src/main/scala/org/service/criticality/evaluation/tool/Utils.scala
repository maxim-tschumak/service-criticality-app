package org.service.criticality.evaluation.tool

import scala.annotation.tailrec

object Utils {

  /**
    * Fractional Ranking of the services. (for better distribution and 'regular' mean rank)
    * @param servicePoints Map of (service -> points)
    * @return Map of (service -> rank)
    */
  def rank(servicePoints: Map[String, Double]): Map[String, Double] = {
    @tailrec
    def rank(servicePoints: Map[String, Double], ranking: Map[String, Double], prevRank: Double): Map[String, Double] = {
      if (servicePoints.isEmpty) ranking
      else {
        val serviceWithMaxPoints = servicePoints.maxBy(_._2)
        val servicesWithMaxPoints = servicePoints.filter(_._2 == serviceWithMaxPoints._2)
        val servicesRank = prevRank + (servicesWithMaxPoints.size.toDouble + 1) / 2
        val servicesRanking = servicesWithMaxPoints.map(serviceEntry => serviceEntry.copy(_2 = servicesRank))

        rank(servicePoints -- servicesWithMaxPoints.keys, ranking ++ servicesRanking, prevRank + servicesWithMaxPoints.size)
      }
    }

    rank(servicePoints, Map(), 0)
  }

  def pearson = sprearsonman _

  def spearman = sprearsonman _

  /**
    * The only difference between Spearman and Pearson calculation is the type of data sets:
    * raw data vs. ranking.
    */
  def sprearsonman(vector: Map[String, Double], vectorCompareTo: Map[String, Double]): Double = {
    require(vector.keySet == vectorCompareTo.keySet)

    val meanPoints = vector.values.sum / vector.size
    val meanCompareToPoints = vectorCompareTo.values.sum / vectorCompareTo.size

    val numerator = (for ((key, points) <- vector)
      yield (points - meanPoints) * (vectorCompareTo(key) - meanCompareToPoints)
      ).sum

    val denominatorPartA = vector.values.map(x => math.pow(x - meanPoints, 2)).sum
    val denominatorPartB = vectorCompareTo.values.map(x => math.pow(x - meanCompareToPoints, 2)).sum

    numerator / (math.pow(denominatorPartA, 0.5) * math.pow(denominatorPartB, 0.5))
  }
}
