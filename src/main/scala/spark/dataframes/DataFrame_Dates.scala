package spark.dataframes

import template.spark.InitSpark
import org.apache.spark.sql.functions._

/**
  * Created by simion
  */
object DataFrame_Dates extends InitSpark{

  def main(args: Array[String]) = {
    import spark.implicits._ //allows to use $

    val df = spark
      .read
      .option("header", "true") //removes the header from the DF into a separate row
      .option("inferSchema", "true") // attaches types to the columns
      .csv("data/CitiGroup2006_2008")

    println("----------------------------------------------------------------")

    // Lot's of options here

    df.select(month(df("Date"))).show()

    df.select(year(df("Date"))).show()

    println("----------------------------------------------------------------")
    // Example
    val df2 = df.withColumn("Year",year(df("Date")))

    // Mean per Year
    val dfavgs = df2.groupBy("Year").mean()
    dfavgs.select($"Year",$"avg(Close)").show()

    close

  }
}
