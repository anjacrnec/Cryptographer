package com.appbundles.exercises

import rita.RiTa
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class RandomUtil {

    companion object {
        fun generateArray(num: Int): Array<Int> {
            val array = IntArray(num)
            for (i in 0 until num)
                array[i] = i + 1
            return array.toTypedArray()
        }

        fun shuffleArray(array: Array<Int>): String {
            val list = arrayListOf<Int>(*array)
            list.shuffle()
            val shuffledArray = list.toArray<Int>(array)
            var shuffled = Arrays.toString(shuffledArray)
            shuffled = shuffled.replace(" ", "")
            shuffled = shuffled.replace(",", "")
            shuffled = shuffled.replace("[", "")
            shuffled = shuffled.replace("]", "")
            return shuffled
        }

        fun randomItem(map: HashMap<String, Boolean>): String {

            val array = ArrayList<String>()
            for ((key, value) in map) {
                if (value)
                    array.add(key)
            }
            return RiTa.randomItem(array) as String
        }
    }

}