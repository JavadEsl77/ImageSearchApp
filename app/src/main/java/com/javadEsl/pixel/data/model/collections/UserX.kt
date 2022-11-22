package com.javadEsl.pixel.data.model.collections


import com.google.gson.annotations.SerializedName
import com.javadEsl.pixel.data.model.allPhotos.ProfileImageX

data class UserX(
    @SerializedName("accepted_tos")
    val acceptedTos: Boolean?,
    val bio: String?,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("for_hire")
    val forHire: Boolean?,
    val id: String?,
    @SerializedName("instagram_username")
    val instagramUsername: String?,
    @SerializedName("last_name")
    val lastName: String?,
    val links: LinksXX?,
    val location: String?,
    val name: String?,
    @SerializedName("portfolio_url")
    val portfolioUrl: String?,
    @SerializedName("profile_image")
    val profileImage: ProfileImageX?,
    val social: Social?,
    @SerializedName("total_collections")
    val totalCollections: Int?,
    @SerializedName("total_likes")
    val totalLikes: Int?,
    @SerializedName("total_photos")
    val totalPhotos: Int?,
    @SerializedName("twitter_username")
    val twitterUsername: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    val username: String?
)