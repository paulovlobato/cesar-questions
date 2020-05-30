package com.victordevelopment.questao4

import java.util.*

class MovieService {

    private val movieList = arrayListOf(
        "Matrix",
        "The Lord of the Rings",
        "Into the Wild",
        "The Big Lebowski",
        "The Shawshank Redemption",
        "The Godfather",
        "Citizen Kane",
        "Psicose",
        "Taxi Driver",
        "Pulp Fiction",
        "Apocalypse Now",
        "Star Wars",
        "Saving Private Rayan",
        "The Shinning",
        "Blade Runner",
        "Titanic",
        "One Flew Over the Cuckoo's Nest",
        "Mad Max",
        "Toy Story",
        "Inception",
        "Alien"
    )

    fun filter(params: String) = movieList.filter { validate(params, it) }

    /**
     * Return all
     */
    fun list() = movieList

    private fun validate(search: String, word: String): Boolean {

        val _search: String = search.toLowerCase(Locale.ROOT).replace("\\s".toRegex(), "")
        val _word: String = word.toLowerCase(Locale.ROOT).replace("\\s".toRegex(), "")

        if (_search == _word) return true

        var count = 0
        if (isPartialPermutation(_search, _word)) {
            count++
        }
        if (hasTypos(_search, _word)) {
            count++
        }

        return count == 1

    }

    private fun isPartialPermutation(original: String, possiblePermutation: String): Boolean {
        val MAX_ALLOWED_WORD = 3

        if (possiblePermutation.length == original.length) {
            if (original[0] == possiblePermutation[0]) {
//                val possiblePermutationLength = possiblePermutation.length
                var changedOccurrences = 0

                if (original[0] != possiblePermutation[0]) {
                    return false
                }

                possiblePermutation.forEachIndexed { idx, c ->
                    val indexFirst = original.indexOf(c)
                    if (indexFirst == -1) {
                        return false
                    }
                    if (idx != indexFirst) {
                        changedOccurrences++
                    }
                }

                if (possiblePermutation.length > MAX_ALLOWED_WORD) {
                    if (changedOccurrences < ((2f / 3f) * possiblePermutation.length)) {
                        return true
                    }
                } else {
                    return changedOccurrences > 0
                }

            }
        }

        return false
    }

    private fun hasTypos(search: String, word: String): Boolean {
        if (search == word) return false

        var _diff = 0
        var _wordAux = word
        var _searchAux = search
        when {
            search.length == word.length -> {
                search.forEachIndexed { idx, c ->
                    if (word[idx] != c) {
                        _diff++
                    }
                }
            }
            search.length > word.length -> {
                var diffLength = search.length - word.length
                while (diffLength > 0) {
                    _wordAux += " "
                    diffLength--
                }
                search.forEachIndexed { index, c ->
                    if (c != _wordAux[index]) {
                        _wordAux = _wordAux.substring(0, index) + c + _wordAux.substring(index, _wordAux.length - 1)
                        _diff++
                    }
                }
            }
            else -> {
                var diffLength = word.length - search.length
                while (diffLength > 0) {
                    _searchAux += " "
                    diffLength--
                }
                word.forEachIndexed { index, c ->
                    if (c != _searchAux[index]) {
                        _searchAux = _searchAux.substring(0, index) + c + _searchAux.substring(index, _searchAux.length - 1)
                        _diff++
                    }
                }
            }
        }
        return _diff == 1
    }

}