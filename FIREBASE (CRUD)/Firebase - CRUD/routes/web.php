<?php

use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

use App\Http\Controllers\FirebaseController;
use App\Events\TaskEvent;

// Requests

Route::get('/', [FirebaseController::class, 'index']);
Route::get('requests', [FirebaseController::class, 'index']);

// Menu

Route::get('menu', [FirebaseController::class, 'menuView']);

// Add

Route::get('add', [FirebaseController::class, 'addView']);
Route::post('add', [FirebaseController::class, 'addDrink']);

// Edit

Route::get('edit/{id}', [FirebaseController::class, 'editView'])->name('edit');
Route::post('edit/{id}', [FirebaseController::class, 'editDrink']);

// Delete

Route::get('delete/{id}', [FirebaseController::class, 'deleteDrink'])->name('delete');

// Table (AJAX)

Route::get('tableAdmin', [FirebaseController::class, 'table'])->name('table');

// Brands

Route::get('brands', [FirebaseController::class, 'brandsView']);
Route::post('brands', [FirebaseController::class, 'addBrand']);

// Coupons

Route::get('coupons', [FirebaseController::class, 'couponsView']);
Route::post('coupons', [FirebaseController::class, 'addCoupon']);