package spark.dataframes

import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema
import org.apache.spark.sql.functions._
import template.spark.InitSpark

/**
  * Created by simion
  */
object DataFrame_MissingData extends InitSpark{

  def main(args: Array[String]) = {
    import spark.implicits._ //allows to use $

    val df = spark
      .read
      .option("header", "true") //removes the header from the DF into a separate row
      .option("inferSchema", "true") // attaches types to the columns
      .csv("data/ContainsNull.csv")

    df.printSchema()

    // Notice the missing values!
    df.show()

    // Handle Na fields
    // 1. Keep them
    // 2. Drop them
    // 3. Fill in with a value

    println("----------------")
    println("Drop rows")
    // drop all rows with any number of columns with Na
    df.na.drop().show()

    // drop rows that have less than the given number of columns with Na
    df.na.drop(2).show() // <2

    println("----------------------------------------------------------------")
    println("Fill in")

    // fill in with Int (only columns with Int type)
    df.na.fill(20).show()

    // fill in String (only columns with Int type)
    df.na.fill("missing").show()


    // Fill in Sales with average sales.
    var avg = df.select(mean("Sales")).head().getDouble(0)

    // Now fill in with the values
    df.na.fill(avg).show()

    close


  }
}
