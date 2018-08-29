package spark.dataframes

import org.apache.spark.sql.functions._

import template.spark.InitSpark

/**
  * Created by simion
  */
object DataFrame_Grouping extends InitSpark{

  def main(args: Array[String]) = {
    import spark.implicits._ //allows to use $

    val df = spark
      .read
      .option("header", "true") //removes the header from the DF into a separate row
      .option("inferSchema", "true") // attaches types to the columns
      .csv("data/Sales.csv")

    df.printSchema()

    // Show
    df.show()

    println("----------------------------------------------------------------")

    // Groupby Columns -> returns a RelationalGroupDataset
    df.groupBy("Company")

    // Mean
    df.groupBy("Company").mean().show()
    // Count
    df.groupBy("Company").count().show()
    // Max
    df.groupBy("Company").max().show()
    // Min
    df.groupBy("Company").min().show()
    // Sum
    df.groupBy("Company").sum().show()

    println("----------------------------------------------------------------")

    // Aggregate Functions
    df.select(countDistinct("Sales")).show() //approxCountDistinct
    df.select(sumDistinct("Sales")).show()
    df.select(variance("Sales")).show()
    df.select(stddev("Sales")).show() //avg,max,min,sum,stddev
    df.select(collect_set("Sales")).show()

    println("----------------------------------------------------------------")

    // OrderBy
    // Ascending
    df.orderBy("Sales").show()

    // Descending
    df.orderBy($"Sales".desc).show()


    close


  }
}
