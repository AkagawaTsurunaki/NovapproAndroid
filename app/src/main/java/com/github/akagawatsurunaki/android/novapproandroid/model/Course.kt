package com.github.akagawatsurunaki.android.novapproandroid.model

import java.math.BigDecimal

data class Course(
    var code: String?,

    var name: String?,

    var credit: BigDecimal?,

    var serialNumber: String?,

    var teachers: String?,

    var onlineContactWay: String?,

    var comment: String?
) {}
