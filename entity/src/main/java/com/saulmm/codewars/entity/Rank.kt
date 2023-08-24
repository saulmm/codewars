package com.saulmm.codewars.entity

enum class Rank(
    val code: String,
    val colorHex: Int,
) {
    KYU_1(code = "1 kyu", colorHex= 0x866CC7),
    KYU_2(code = "2 kyu", colorHex= 0x866CC7),
    KYU_3(code = "3 kyu", colorHex= 0x1F87E7),
    KYU_4(code = "4 kyu", colorHex= 0x1F87E7),
    KYU_5(code = "5 kyu", colorHex= 0xEDB613),
    KYU_6(code = "6 kyu", colorHex= 0xEDB613),
    KYU_7(code = "7 kyu", colorHex= 0xE6E6E6),
    KYU_8(code = "8 kyu", colorHex= 0xE6E6E6),
    KYU_9(code = "9 kyu", colorHex= 0xE6E6E6),
    DAN_1(code = "1 dan", colorHex= 0x555555),
    DAN_2(code = "2 dan", colorHex= 0x555555),
    DAN_3(code = "3 dan", colorHex= 0x555555),
    DAN_4(code = "4 dan", colorHex= 0x555555),
    UKNOWN(code = "unknown", colorHex= 0xAF3E75),
}