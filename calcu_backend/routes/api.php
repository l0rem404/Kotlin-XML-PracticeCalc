<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

use App\Http\Controllers\UserController;
use App\Http\Controllers\HistoryController;

Route::post('/register', [UserController::class, 'register']);
Route::post('/login', [UserController::class, 'login']);
Route::middleware('auth:sanctum')->post('/logout', [UserController::class, 'logout']);

Route::middleware('auth:sanctum')->post('/history', [HistoryController::class, 'store']);
Route::middleware('auth:sanctum')->get('/history', [HistoryController::class, 'index']);
Route::middleware('auth:sanctum')->delete('/history/clear', [HistoryController::class, 'clear']);




Route::post('/forgotPass', [UserController::class, 'forgotPassword']);
Route::post('/resetPass', [UserController::class, 'resetPassword']);
