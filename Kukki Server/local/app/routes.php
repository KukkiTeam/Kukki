<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the Closure to execute when that URI is requested.
|
*/


Route::get('/', function()
{
    if(Session::has('kukki_user')){
        return Redirect::to('site/index');
    }else{
        return Redirect::to('site/login');
    }
	
});

App::missing(function($exception)
{
    // return Response::view('errors.missing', array(), 404);
    return Redirect::to('site/error-page');
});

Route::Controller('site','SiteController');
// API
Route::get('site/active-account', array('uses'=>'SiteController@activeEmail'));


Route::post('', array('uses'=>'UserController@saveImage'));
//Route::get('{id}', array('uses'=>'UserController@getUserAvatar'));
Route::group(array('prefix'=>'upload'), function(){
    Route::group(array('prefix'=>'account'), function(){
        Route::post('', array('uses'=>'UserController@saveImage'));
    });
});
Route::group(array('prefix'=>'api'), function(){
    Route::group(array('prefix'=>'account'), function(){
        Route::get('{id}', array('uses'=>'UserController@getUser'));
        Route::get('avatar/{id}', array('uses'=>'UserController@getAvatar'));
        Route::get('img-cover/{id}', array('uses'=>'UserController@getImageCover'));
        Route::get('speciality/{id}', array('uses'=>'UserController@getSpeciality'));
        Route::post('', array('uses'=>'UserController@saveUser'));
        Route::post('login', array('uses'=>'UserController@login'));
        Route::post('create-receipt', array('uses'=>'UserController@createReceipt'));
        Route::post('update-avatar/{id}', array('uses'=>'UserController@updateAvatar'));
        Route::put('{id}', array('uses'=>'UserController@putData'));
        Route::delete('{id}', array('uses'=>'UserController@deleteUser'));
    });
});