<img width="374" alt="image" src="https://user-images.githubusercontent.com/66052467/172398872-e60a73f5-1dad-444c-bca2-19fcdb9caa96.png">

# TodoMVVM
## Stack

- StaggeredGridLayout
- Room
- Coroutines
- DataBinding
- ViewModel
- LiveData
- RecyclerView (ListAdapter + DiffUtil)
- Navigation
- animations

## Code

+ Room Entity Column에는 기본 자료형만 드갈 수 있기 때문에 TypeConverter를 사용
```kotlin
class Converter {

    @TypeConverter
    fun fromPriority(priority: Priority): String{
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }

}

```

+ 일회성 옵저버 만들기
```kotlin
//한 번만 옵저브하고 버리는 일회성 옵저버
fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>){
    observe(lifecycleOwner, object : Observer<T>{
        override fun onChanged(t: T) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

```

+ BindAdapter 야무지게 써먹기~
```kotlin
package com.hackerton.todomvvm.fragments

import android.view.View
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hackerton.todomvvm.R
import com.hackerton.todomvvm.data.ToDoDatabase
import com.hackerton.todomvvm.data.model.Priority
import com.hackerton.todomvvm.data.model.ToDoData
import com.hackerton.todomvvm.fragments.list.ListFragmentDirections

class BindingAdapters {

    companion object {

        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>) {
            view.isVisible = emptyDatabase.value == true
        }

        @BindingAdapter("android:parsePriorityToInt")
        @JvmStatic
        fun parsePriorityToInt(view: Spinner, priority: Priority) {
            when (priority) {
                Priority.HIGH -> view.setSelection(0)
                Priority.MEDIUM -> view.setSelection(1)
                Priority.LOW -> view.setSelection(2)
            }
        }
        @BindingAdapter("android:parsePriorityColor")
        @JvmStatic
        fun parsePriorityColor(cardView: CardView, priority: Priority){
            when(priority){
                Priority.HIGH -> cardView.setCardBackgroundColor(cardView.context.getColor(R.color.red))
                Priority.MEDIUM -> cardView.setCardBackgroundColor(cardView.context.getColor(R.color.yellow))
                Priority.LOW -> cardView.setCardBackgroundColor(cardView.context.getColor(R.color.green))
            }
        }

        @BindingAdapter("android:sendDataToUpdateFragment")
        @JvmStatic
        fun sendDataToUpdateFragment(view: ConstraintLayout, currentItem: ToDoData){
            view.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
                view.findNavController().navigate(action)
            }
        }
    }

}

```


## Structure
![image](https://user-images.githubusercontent.com/66052467/172396321-cc05913b-fd12-4ffa-84fc-d4b378c4bf54.png)
