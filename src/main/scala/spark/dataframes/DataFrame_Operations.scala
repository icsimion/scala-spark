package spark.dataframes

import template.spark.InitSpark
import org.apache.spark.sql.functions._

/**
  * Created by simion
  */
object DataFrame_Operations extends InitSpark{

  def main(args: Array[String]) = {
    import spark.implicits._ //allows to use $

    val df = spark
      .read
      .option("header", "true") //removes the header from the DF into a separate row
      .option("inferSchema", "true") // attaches types to the columns
      .csv("data/CitiGroup2006_2008")

    println("----------------------------------------------------------------")

    // Filtering
      //Scala notation
//    df.filter($"Close">480).show()
//    df.filter("Close > 480").show() //alternative

    val CH = df.filter($"Close" < 480 && $"High" < 480 )
    CH.show(5)

    println("----------------------------------------------------------------")

    //SQL notation
//    df.filter("Close < 480 AND High < 480").show(5)

    // Collect into an Array object
    val CH_low = CH.collect()

    println("----------------------------------------------------------------")

    // Count
    CH.count()

    println("----------------------------------------------------------------")

    // Filter for equality
    df.filter($"High"===484.40).show()

    //Correlation between 2 columns
    df.select(corr("High", "Low")).show()


    close


  }
}
