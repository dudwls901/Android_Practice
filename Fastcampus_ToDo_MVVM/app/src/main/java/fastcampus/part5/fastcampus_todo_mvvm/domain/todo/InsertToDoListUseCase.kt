package fastcampus.part5.fastcampus_todo_mvvm.domain.todo

import fastcampus.part5.fastcampus_todo_mvvm.data.entity.ToDoEntity
import fastcampus.part5.fastcampus_todo_mvvm.data.repository.ToDoRepository
import fastcampus.part5.fastcampus_todo_mvvm.domain.UseCase

internal class InsertToDoListUseCase(
  private val toDoRepository: ToDoRepository
) : UseCase {
  suspend operator fun invoke(toDoList: List<ToDoEntity>){
    return toDoRepository.insertToDoList(toDoList)
  }
}