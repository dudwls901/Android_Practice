package fastcampus.part5.fastcampus_todo_mvvm.data.repository

import fastcampus.part5.fastcampus_todo_mvvm.data.entity.ToDoEntity

class TestToDoRepository: ToDoRepository {

    //메모리 캐싱 용도
    private val toDoList: MutableList<ToDoEntity> = mutableListOf()

    override suspend fun getToDoList(): List<ToDoEntity> {
        return toDoList
    }

    override suspend fun insertToDoList(toDoList: List<ToDoEntity>) {
        this.toDoList.addAll(toDoList)
    }
}