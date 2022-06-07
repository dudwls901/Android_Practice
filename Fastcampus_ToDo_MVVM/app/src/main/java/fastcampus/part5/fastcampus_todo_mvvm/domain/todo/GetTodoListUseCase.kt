package fastcampus.part5.fastcampus_todo_mvvm.domain.todo

import fastcampus.part5.fastcampus_todo_mvvm.data.entity.ToDoEntity
import fastcampus.part5.fastcampus_todo_mvvm.data.repository.ToDoRepository
import fastcampus.part5.fastcampus_todo_mvvm.domain.UseCase

internal class GetTodoListUseCase(
    private val toDoRepository: ToDoRepository
) : UseCase {

    suspend operator fun invoke() : List<ToDoEntity>{
        return toDoRepository.getToDoList()
    }

}