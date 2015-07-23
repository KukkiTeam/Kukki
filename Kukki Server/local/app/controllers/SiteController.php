<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

class SiteController extends Controller{
    
    
    public function __construct(){
        $this->beforeFilter('require-login', array('except' => array('getLogin', 'postLogin', 'getActiveEmail', 'getLogout', 'getErrorPage')));
    }
    
    public function getIndex(){
        
        $name=Session::get('kukki_user');
        $table=(new User)->getAllUser();
        $side_active=1;
        
        return View::make('site/index', array('admin_name'=>$name,
                'table'=>$table,
                'table_size'=>count($table), 
                'side_active'=>$side_active
                ));
    }
    public function getRecipes(){
        $name=Session::get('kukki_user');
        $table=(new Post)->getRecipes();
        $side_active=1;
       // var_dump($table);
        
        return View::make('site/recipes', array('admin_name'=>$name,
                'table'=>$table,
                'table_size'=>count($table), 
                'side_active'=>$side_active
                ));
         
    }
    public function getLogin(){
        return View::make('site/login');
    }

    public function getLogout(){
        if(Session::has('kukki_user')){
            Session::forget('kukki_user');
            return Redirect::to('site/login');
        }
    }
    public function getErrorPage(){
        return View::make('site/error-page');
    }

    public function postLogin(){
        $input=Input::all();
        $email=$input['email'];
        $arr_error=array();
        $password=$input['password'];
        $check=DB::table('user')->where('email', $email)->where('password', md5(sha1($password)))->where('Role_id','1')->get();
        if(count($check)>0){
            $actived=$check[0]->active;
            if($actived==null){
                $name=$check[0]->name;
                Session::put('kukki_user', $name);
                return Redirect::to('site/index');
            }else{
                array_push($arr_error, 'Account not actived, Please Actived before Login !');
                return Redirect::back()->with('errors', $arr_error);
            }
            
        }else{
            array_push($arr_error, 'Email or Password not Valid !');
            return Redirect::back()->with('errors', $arr_error);
        }
    }
    
    public function getSetAdmin(){
        $name=Session::get('kukki_user');
        $table=(new User)->getAllUser();
        $side_active=2;
        return View::make('site/set-admin', array('admin_name'=>$name,
                'table'=>$table,
                'table_size'=>count($table),
                'side_active'=>$side_active
                ));
    }
    public function postSetAdmin(){
        try{
        $input = Input::all();
        $id = $input['input_id'];
        $email=$input['input_email'];
        $role_id=$input['role'];
        $errors=array();
        
        if($role_id==0){
            array_push($errors, 'Please select Role !');
            return Redirect::back()->with('errors', $errors);
        }
        
        $user=User::find($id);
        $user->Role_id=$role_id;
        $user->save();
        
        if($role_id==1){
            array_push($errors, 'Set Admin successfully to '.$email);
        }else{
            array_push($errors, 'Set Member successfully to '.$email);
        }
        
        return Redirect::back()->with('errors', $errors);
        }  catch (Exception $ex){
            array_push($errors, $ex->getMessage());
            return Redirect::back()->with('errors', $errors);
        }
    }

        public function getActiveEmail(){
        $input=Input::all();
        $email=$input['email'];
        $active=$input['activation'];
        $errors=array();
        
        $check=DB::table('user')->where('email',$email)->where('active', $active)->get();
       // var_dump($check[0]->id);
        if(count($check)>0){
          $id=$check[0]->id;
          $user=User::find($id);
       //   var_dump($user);
          
          $user->active=null;
          if($user->save()){
              array_push($errors, 'Email has been actived successfully !');
              return Redirect::to('site/login')->with('errors', $errors);
          }
        }else{
            return View::make('site/error-page') ;
        }
         
         
    }
    
    
}