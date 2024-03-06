package com.chaeda.data.service

import com.chaeda.data.model.request.RequestImageInfo
import com.chaeda.data.model.response.PresignedResponse
import com.chaeda.domain.entity.ImageInfo
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface HomeworkService {

    /**
     * api related to images
     */

    @POST("/api/images/presigned-url/{memberId}")
    suspend fun getPresignedUrl(
        @Path("memberId") memberId: Int,
        @Body requestImageInfo: RequestImageInfo
    ): PresignedResponse

    @Multipart
    @POST("/api/images/upload") // 실제 API 엔드포인트를 여기에 입력
    suspend fun uploadImages(
        @Part files: List<MultipartBody.Part>
    ): String

    @POST("/api/images/presigned-url/complete/{memberId}")
    suspend fun noticePresignedUrl(
        @Path("memberId") memberId: Int,
        @Body imageInfo: ImageInfo
    ): ResponseBody

    @POST("/api/images/display/{memberId}")
    suspend fun getImagesUrl(
        @Path("memberId") memberId: Int,
        @Body images: List<ImageInfo>
    ): List<String>

}