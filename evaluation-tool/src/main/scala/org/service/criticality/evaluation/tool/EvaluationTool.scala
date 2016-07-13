package org.service.criticality.evaluation.tool

import java.io.File

object EvaluationTool {

  val argumentParser = new scopt.OptionParser[EvalToolConfig]("Survey Calculator") {
    opt[File]("survey-results").abbr("s").action((s, c) => c.copy(survey = s)).text("survey results in CSV format")
    opt[File]("framework-results").abbr("f").action((f, c) => c.copy(framework = f)).text("framework results in CSV format")
    opt[String]("correlation-type").abbr("c").action((t, c) => c.copy(correlation = t)).text("correlation type (spearman|pearson)")
    opt[Seq[String]]("exclude").abbr("e").action((e, c) => c.copy(exclude = e)).text("services to exclude from evaluation")
    checkConfig(config =>
      if (!config.valid) failure("please check the parameters")
      else success
    )
  }

  def main(args: Array[String]): Unit = {
    argumentParser.parse(args, EvalToolConfig()) match {
      case Some(config) => evaluate(config)
      case None => println("please check the parameters"); System.exit(1);
    }
  }

  private def evaluate(config: EvalToolConfig): Unit = {
    val surveyResults = Parser.surveyResults(config.survey).without(config.exclude)
    val frameworkResults = Parser.frameworkResults(config.framework).without(config.exclude).normalized

    val evaluation = Evaluation(surveyResults, frameworkResults, Correlation.withName(config.correlation))

    println(evaluation)
  }

  case class EvalToolConfig(survey: File = new File("."),
                            framework: File = new File("."),
                            exclude: Seq[String] = Seq(),
                            correlation: String = "spearman") {

    lazy val valid = survey.exists() && framework.exists() && (Correlation.values.map(_.toString) contains correlation)
  }

}
