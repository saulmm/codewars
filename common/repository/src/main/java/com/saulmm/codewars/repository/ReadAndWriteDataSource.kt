package com.saulmm.codewars.repository

interface ReadAndWriteDataSource<T, Q>:
    ReadableDataSource<T, Q>,
    WritableDataSource<T, Q>