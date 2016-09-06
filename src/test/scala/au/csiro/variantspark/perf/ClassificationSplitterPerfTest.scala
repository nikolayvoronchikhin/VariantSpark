package au.csiro.variantspark.perf

import org.junit.Assert._
import org.junit.Test;
import au.csiro.variantspark.algo.ClassificationSplitter
import it.unimi.dsi.util.XorShift1024StarRandomGenerator
import org.apache.spark.mllib.linalg.Vectors
import au.csiro.pbdava.ssparkle.common.utils.Timed
import au.csiro.variantspark.algo.JClassificationSplitter
import scala.collection.immutable.BitSet
import au.csiro.variantspark.algo.JConfusionClassificationSplitter

class ClassificationSplitterPerfTest {
  
  @Test
  def testJSingleSplitter() {
    
    val rg = new XorShift1024StarRandomGenerator(13)
    val nLabels = 10000
    val labels = Array.fill(nLabels)(Math.abs(rg.nextInt) % 2)
    val sp = new JClassificationSplitter(labels,2, 3)
    val splitIndexes = Range(0, 10000).toArray
    val data = Array.fill(nLabels)((Math.abs(rg.nextInt()) % 3).toDouble)
    Timed.time {
      for (i <- 0 until 50000) {     
        sp.findSplit(data, splitIndexes)
      }
    }.report("BasicSplitter-1")
    Timed.time {
      for (i <- 0 until 50000) {     
        sp.findSplit(data, splitIndexes)
      }
    }.report("BasicSplitter-2")
        Timed.time {
    for (i <- 0 until 50000) {     
        sp.findSplit(data, splitIndexes)
      }
    }.report("BasicSplitter-3")
  }
  
  @Test
  def testFastSingleSplitter() {
    
    val rg = new XorShift1024StarRandomGenerator(13)
    val nLabels = 10000
    val labels = Array.fill(nLabels)(Math.abs(rg.nextInt) % 2)
    val sp = new JConfusionClassificationSplitter(labels, 2, 3);
    val splitIndexes = Range(0, nLabels).toArray
    val data = Array.fill(nLabels)((Math.abs(rg.nextInt()) % 3).toDouble)
    Timed.time {
      for (i <- 0 until 50000) {     
        sp.findSplit(data, splitIndexes)
      }
    }.report("ConfusionSplitter-1")
    Timed.time {
      for (i <- 0 until 50000) {     
        sp.findSplit(data, splitIndexes)
      }
    }.report("ConfusionSplitter-2")
        Timed.time {
    for (i <- 0 until 50000) {     
        sp.findSplit(data, splitIndexes)
      }
    }.report("ConfusionSplitter-3")
  }

  
// TODO (Idea): This is an idea for a fast splitter based on bitmaps 
// should be very efficient on GPUs
//
//  def findBitmapSplit(data:Array[BitSet], labels:Array[BitSet], split:BitSet) = {
//    // assume enrtire set
//    
//    val totalCount= labels.map(l => ( l & split ).size).toArray
//    for (i <- 0 until data.length -1) {
//      val leftCount = labels.map(l => ( l & split & data(i)).size).toArray
//    }
//  }
//  
//
//  def testBits {
//    val rg = new XorShift1024StarRandomGenerator(13)
//    val nLabels = 10000
//    val labels = Array.fill(nLabels)(Math.abs(rg.nextInt) % 2)
//    val splitIndexes = Range(0, 10).toArray
//    val data = Array.fill(nLabels)((Math.abs(rg.nextInt()) % 3).toByte)   
//    // encode labels as bytes
//    val bSplit = BitSet(splitIndexes:_*)
//    val bLables = Range(0,2).map(i => BitSet(labels.indices.filter(labels(_) == i).toArray:_*)).toArray
//    val bData = Range(0,3).map(i => BitSet(data.indices.filter(labels(_) == i).toArray:_*)).toArray
//    Timed.time {
//      for (i <- 0 until 50000) {     
//        findBitmapSplit(bData, bLables, bSplit)
//      }
//    }.report("Splitting")
//    Timed.time {
//      for (i <- 0 until 50000) {     
//        findBitmapSplit(bData, bLables, bSplit)
//      }
//    }.report("Splitting1")
//    Timed.time {
//      for (i <- 0 until 50000) {     
//        findBitmapSplit(bData, bLables, bSplit)
//      }
//    }.report("Splitting2")  
//  }
  

}