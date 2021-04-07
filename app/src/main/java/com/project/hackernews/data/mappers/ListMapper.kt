package com.project.hackernews.data.mappers

// Non-nullable to Non-nullable
interface ListMapper<I, O>: IMapper<List<I>, List<O>>

class ListMapperImpl<I, O>(
    private val mapper: IMapper<I, O>
) : ListMapper<I, O> {
    override fun map(input: List<I>): List<O> {
        return input.map { mapper.map(it) }
    }
}