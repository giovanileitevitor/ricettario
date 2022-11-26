package br.applabbs.ricettario

import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

class GetResults{

    @Test
    fun maximumValue(){
        //arrange
        val fakeList = arrayListOf<Int>(10,8,6,3)
        val maximumSum = getMaxSum(itens = fakeList)

        assertEquals(maximumSum, 17)
    }

    @Test
    fun minimumValue(){
        //arrange
        val fakeList = arrayListOf<Int>(10,8,6,3)
        val minimumSum = getMinSum(itens = fakeList)

        assertEquals(minimumSum,24 )
    }

    @Test
    fun findPosition(){
        val fakeList = intArrayOf(3, 6, 10, 8, 15, 19, 20, 30, 45, 55, 72, 90)
        print("${fakeList.contentToString()} \n")

        val searchedValue = 15
        val result = buscaBinaria(vetor = fakeList, value = searchedValue, init = 0, end = fakeList.size)
        val result2 = fakeList.binarySearch(searchedValue)

        print("Searched value: $searchedValue \n")
        print("Position: $result \n\n\n")

        print("Searched value: $searchedValue \n")
        print("Position2: $result2 \n\n\n")
    }

    private fun buscaBinaria(vetor: IntArray, value: Int, init: Int, end: Int): Int{
        val vetorSize = init + end
        val half = vetorSize / 2

        if(vetor[half] == value){
            return half
        }

        if(init > end){
            return -1
        }

        if(vetor[half] > value){
            return buscaBinaria(vetor, value, init, half-1)
        }
        else{
            return buscaBinaria(vetor, value, half+1, end)
        }

    }

    private fun getMinSum(itens: List<Int>): Int{
        val menorItem = itens.minOrNull()
        var result : Int = 0

        for(i in itens.indices){
            if(itens[i] != menorItem){
                result += itens[i]
            }
        }
        return result
    }

    private fun getMaxSum(itens: List<Int>): Int{
        val maiorItem = itens.maxOrNull()
        var result : Int = 0
        for(i in itens.indices){
            if(itens[i] != maiorItem){
                result += itens[i]
            }
        }
        return result
    }


}
