package com.chaeda.chaeda.presentation.assignment.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.chaeda.base.BindingActivity
import com.chaeda.base.util.extension.boolExtra
import com.chaeda.base.util.extension.longExtra
import com.chaeda.base.util.extension.setOnSingleClickListener
import com.chaeda.chaeda.R
import com.chaeda.chaeda.databinding.ActivityAssignmentDetailBinding
import com.chaeda.chaeda.presentation.assignment.AssignmentState
import com.chaeda.chaeda.presentation.assignment.result.AssignmentResultActivity
import com.chaeda.chaeda.presentation.assignment.result.submit.ResultSubmitActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AssignmentDetailActivity
    : BindingActivity<ActivityAssignmentDetailBinding>(R.layout.activity_assignment_detail) {

    private val id by longExtra()
    private val isDone by boolExtra()

    private val assignmentDetailViewModel by viewModels<AssignmentDetailViewModel>()
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private var sp = 0
    private var ep = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initListener()
        setResultLauncher()
        observe()
    }

    private fun setResultLauncher() {
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK){
                val isDoneNow = result.data?.getBooleanExtra("isDone", false)
                if (isDoneNow == true) {
                    binding.tvFab.text = "오답 확인하기"
                }
            }
        }
    }

    private fun initListener() {
        with(binding) {
            tvFab.text = if (isDone) "오답 확인하기" else "검사 시작하기"
            llBack.setOnSingleClickListener {
                finish()
            }
            fab.setOnSingleClickListener {
                if (isDone) startActivity(AssignmentResultActivity.getIntent(this@AssignmentDetailActivity, id))
                else {
//                    resultLauncher.launch(ConfirmSubmitActivity.getIntent(this@HomeworkDetailActivity))
                    startActivity(ResultSubmitActivity.getIntent(this@AssignmentDetailActivity, id, tvTitle.text.toString(), sp, ep, tvDeadline.text.toString()))
                }
            }
        }
        assignmentDetailViewModel.getAssignmentById(id = id)
    }

    private fun observe() {
        lifecycleScope.launch {
            assignmentDetailViewModel.assignmentState.collect { state ->
                when (state) {
                    is AssignmentState.GetByIdSuccess -> {
                        val assignment = state.assignment
                        with(binding) {
                            tvTitle.text = assignment.title
                            tvRange.text = "${assignment.startPage}p - ${assignment.endPage}"
                            sp = assignment.startPage
                            ep = assignment.endPage
                            tvDeadline.text = assignment.targetDate
                            tvTextbook.text = assignment.textbook?.name
                            ivThumbnail.load(assignment.textbook?.imageUrl)
                        }
                    }
                    else -> { }
                }
            }
        }
    }

    companion object {

        // 요청 코드 정의
        private const val REQUEST_CODE = 204

        // 액티비티 시작
        fun getIntent(context: Context, id: Long) = Intent(context, AssignmentDetailActivity::class.java).apply {
            putExtra("id", id)
        }

        fun getIntent(context: Context, id: Long, isDone: Boolean) = Intent(context, AssignmentDetailActivity::class.java).apply {
            putExtra("id", id)
            putExtra("isDone", isDone)
        }
    }
}