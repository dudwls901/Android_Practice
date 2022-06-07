package fastcampus.part5.hiltpractice.view

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SecondFragment : Fragment() {

    @InMemorryLogger
    @Inject
    lateinit var logger: LoggerLocalDataSource

    @Inject
    private lateinit var dateFormatter: DateFormatter

    override fun onResume() {
        super.onResume()
        logger.getAllLogs { logs ->
            recyclerView.adapter = LogsViewAdapter(logs, dateFormatter) }

    }
}