package com.project.hackernews.data.mappers

interface IMapper<I, O> {
    fun map(input: I): O
    fun mapReverse(input: O): I
}