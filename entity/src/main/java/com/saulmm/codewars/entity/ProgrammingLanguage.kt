package com.saulmm.codewars.entity

enum class ProgrammingLanguage(
    val displayName: String,
    val colorHex: Int
) {
    HASKELL("Haskell", 0x5e5086),
    JAVASCRIPT("JavaScript", 0xf1e05a),
    PYTHON("Python", 0x3572A5),
    COFFEESCRIPT("CoffeeScript", 0x244776),
    RUBY("Ruby", 0x701516),
    CSHARP("C#", 0x178600),
    R("R", 0x198CE7),
    COBOL("COBOL", 0x4F6457),
    RUST("Rust", 0xDEA584),
    FACTOR("Factor", 0x636746),
    CPP("C++", 0xf34b7d),
    JAVA("Java", 0x5382A1),
    C("C", 0x555555),
    LAMBDACALC("Lambda Calculus", 0xFFFFFF),
    GO("Go", 0x00ADD8),
    PHP("PHP", 0x4F5D95),
    SHELL("Shell", 0x89E051),
    TYPESCRIPT("TypeScript", 0x2b7489),
    ELIXIR("Elixir", 0x6e4a7e),
    SCALA("Scala", 0xc22d40),
    DART("Dart", 0x00B4AB),
    JULIA("Julia", 0xa270ba),
    COMMONLISP("Common Lisp", 0x3fb68b),
    CRYSTAL("Crystal", 0x000100),
    LUA("Lua", 0x000080),
    D("D", 0xba595e),
    FSHARP("F#", 0xb845fc),
    CLOJURE("Clojure", 0xdb5855),
    GROOVY("Groovy", 0xe69f56),
    RACKET("Racket", 0x22228f),
    KOTLIN("Kotlin", 0xF18E33),
    UNKNOWN("UNKNOWN", 0xFFF)
}
