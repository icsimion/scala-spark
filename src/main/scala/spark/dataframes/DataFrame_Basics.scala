package spark.dataframes

import template.spark.InitSpark

/**
  * Created by simion.
  */
object DataFrame_Basics extends InitSpark{

  def main(args: Array[String]) = {

    val df = spark
      .read
      .option("header", "true") //removes the header from the DF into a separate row
      .option("inferSchema", "true") // attaches types to the columns
      .csv("data/CitiGroup2006_2008")

    for (row <- df.head(5)) {
      println(row)
    }

    df.describe().show() // summary: count, mean, stddev, min, max

    println("----------------------------------------------------------------")

    // Show columns
    df.select("Volume").show(4) // show only column Volume
    df.select("Date", "Close").show(4) // show Date and Close column, with the specific types

    println("----------------------------------------------------------------")

    // Create new column
//    df("High") // selects the column
    val df2 = df.withColumn("HighPlusLow", df("High")+df("Low"))
    df2.printSchema()

    // Rename a column
    df2.select(df2("HighPlusLow").as("HPL")).show(5)

    close

  }


}

