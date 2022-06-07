package fastcampus.part5.fastcampus_todo_mvvm.data.repository

import fastcampus.part5.fastcampus_todo_mvvm.data.entity.ToDoEntity


/*
* 1. insertToDoList
* 2. getToDoList
*
* */
interface ToDoRepository {
  //코루틴 이용
    suspend fun getToDoList(): List<ToDoEntity>

    suspend fun insertToDoList(toDoList: List<ToDoEntity>)

}