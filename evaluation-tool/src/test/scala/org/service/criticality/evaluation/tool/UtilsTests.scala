package org.service.criticality.evaluation.tool

import org.scalatest._

class UtilsTests extends FlatSpec with Matchers {

  "Ranking method" should "be able to rank a simple entry" in {
    val entries = Map("serviceA" -> 100.0)

    Utils.rank(entries) shouldBe Map("serviceA" -> 1)
  }

  it should "be able to rank two services with different values" in {
    val entries = Map("serviceA" -> 100.0, "serviceB" -> 200.0)

    Utils.rank(entries) shouldBe Map("serviceB" -> 1, "serviceA" -> 2)
  }

  it should "be able to rank two services with the same value" in {
    val entries = Map("serviceA" -> 100.0, "serviceB" -> 10.0, "serviceC" -> 10.0, "serviceD" -> 5.0)

    Utils.rank(entries) shouldBe Map("serviceA" -> 1, "serviceB" -> 2.5, "serviceC" -> 2.5, "serviceD" -> 4)
  }

  it should "be able to rank multiple services with the same value" in {
    val entries = Map("serviceA" -> 100.0, "serviceB" -> 10.0, "serviceC" -> 10.0, "serviceD" -> 10.0, "serviceE" -> 1.0)

    Utils.rank(entries) shouldBe Map("serviceA" -> 1, "serviceB" -> 3, "serviceC" -> 3, "serviceD" -> 3, "serviceE" -> 5)
  }

  "Spearman correlation" should "be 1 iff rankings are the same" in {
    val ranking1 = Map("service1" -> 1.0, "service2" -> 2.0)
    val ranking2 = Map("service1" -> 1.0, "service2" -> 2.0)

    val correlation = Utils.spearman(ranking1, ranking2)

    correlation shouldBe 1.0 +- 0.0001
  }

  it should "be -1 for reverse rankings" in {
    val ranking1 = Map("service1" -> 1.0, "service2" -> 2.0, "service3" -> 3.0)
    val ranking2 = Map("service1" -> 3.0, "service2" -> 2.0, "service3" -> 1.0)

    val correlation = Utils.spearman(ranking1, ranking2)

    correlation shouldBe -1.0 +- 0.0001
  }

  it should "be able to compare different rankings with same assignments" in {
    val ranking1 = Map("service1" -> 1.0, "service2" -> 2.0, "service3" -> 3.0)
    val ranking2 = Map("service1" -> 1.0, "service2" -> 2.5, "service3" -> 2.5)

    val correlation = Utils.spearman(ranking1, ranking2)

    correlation shouldBe math.pow(1.5 / 2.0, 0.5) +- 0.0001
  }

  it should "be able to compare different rankings without same assignments" in {
    val ranking1 = Map("service1" -> 1.0, "service2" -> 2.0, "service3" -> 3.0)
    val ranking2 = Map("service1" -> 1.0, "service2" -> 3.0, "service3" -> 2.0)

    val correlation = Utils.spearman(ranking1, ranking2)

    correlation shouldBe 0.5 +- 0.0001
  }

  "Pearson correlation" should "be 1 iff the vectors are the same" in {
    val surveyPoints = Map("service1" -> 1.0, "service2" -> 2.0, "service3" -> 3.0)
    val aggregatedMetrics = Map("service1" -> 1.0, "service2" -> 2.0, "service3" -> 3.0)

    val correlation = Utils.pearson(surveyPoints, aggregatedMetrics)

    correlation shouldBe 1.0 +- 0.0001
  }

  it should "be -1 for reverse values in the vectors" in {
    val surveyPoints = Map("service1" -> 1.0, "service2" -> 2.0, "service3" -> 3.0)
    val someMetric = Map("service1" -> 3.0, "service2" -> 2.0, "service3" -> 1.0)

    val correlation = Utils.pearson(surveyPoints, someMetric)

    correlation shouldBe -1.0 +- 0.0001
  }

  it should "be able to compare different vectors" in {
    val surveyPoints = Map("service1" -> 1.0, "service2" -> 2.0, "service3" -> 3.0)
    val someMetric = Map("service1" -> 1.0, "service2" -> 3.0, "service3" -> 2.0)

    val correlation = Utils.pearson(surveyPoints, someMetric)

    correlation shouldBe 0.5 +- 0.0001
  }
}
