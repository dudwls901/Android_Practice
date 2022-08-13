open class Event<out T>(private val content: T) {
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) { // 이벤트가 이미 처리 되었다면
            null // null을 반환하고,
        } else { // 그렇지 않다면
            hasBeenHandled = true // 이벤트가 처리되었다고 표시한 후에
            content // 값을 반환합니다.
        }
    }

    /**
     * 이벤트의 처리 여부에 상관 없이 값을 반환합니다.
     */
    fun peekContent(): T = content
}

/*
* observe에서 매번 getContentIfNotHandled()?.let{}으로 null 체크 해야 하는 수고를 없애주는 옵저버
*/
class EventObserver<T>(private val onEventUnHandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let{ value->
            onEventUnHandledContent(value)
        }
    }
}

//사용

//in ViewModel
    // 토스트 메시지 내용
    private val _toastMessage = MutableLiveData<Event<String>>()
    val toastMessage: LiveData<Event<String>> = _toastMessage

//in View
orderViewModel.orderComplete.observe(this, EventObserver {
            //주문 완료한 이후에 팝업 띄워주기
            if (it) {
                //이미 한 번 팝업 뜬 적 있다면
                if (StoreApplication.alertedBeaconDialog) return@EventObserver
                startScan()
            }
        })
