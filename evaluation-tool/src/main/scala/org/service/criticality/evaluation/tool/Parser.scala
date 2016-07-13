package org.service.criticality.evaluation.tool

import java.io.File
import scala.io.Source

object Parser {
  private val delimiter = ",[ \t]+"

  private def parse(file: File): Seq[Entry] = {
    val lines = Source.fromFile(file).getLines().toList
    val horizontalMetaNames = lines.head.split(delimiter).toList.tail

    for (
      line <- lines.tail;
      lineEntries = line.split(delimiter).toList;
      verticalMetaName = lineEntries.head;
      values = lineEntries.tail;
      i <- values.indices
    ) yield Entry(verticalMetaName, horizontalMetaNames(i), values(i).toDouble)
  }

  case class Entry(horizontalMetaName: String, verticalMetaName: String, value: Double)

  def surveyResults(file: File): SurveyResults = SurveyResults(parse(file))

  def frameworkResults(file: File): FrameworkResults = FrameworkResults(parse(file))

  implicit def metricValueWrapper(entries: Seq[Entry]): Seq[MetricValue] =
    entries.map(e => MetricValue(e.horizontalMetaName, e.verticalMetaName, e.value))

  implicit def voteWrapper(entries: Seq[Entry]): Seq[Vote] =
    entries.map(e => Vote(e.horizontalMetaName, e.verticalMetaName, e.value.toInt))
}

