<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
class UserController extends Controller{
     protected $Users=null;
    public function __construct(User $users) {
        $this->Users=$users;
    }
    
    public function getUser($id){
        return Response::json(array('var'=>$id), 201);
    }


    public function saveUser(){
        $result=$this->Users->saveUser();
        if($result['error']==false){
            return Response::json(array('error'=>false, 'message'=>$result['message']), 201);
        }else{
            return Response::json(array('error'=>true, 'message'=>$result['message']), 200);
        }
    }
    public function getSpeciality($id){
        $result=$this->Users->getSpeciality($id);
        
        if($result['error']==false){
            return Response::json(array('error'=>false, 'message'=>$result['message']), 201);
        }else{
            return Response::json(array('error'=>true, 'message'=>$result['message']), 200);
        }
    }

    public function getAvatar($id){
        
        $result=$this->Users->getPathAvatar($id);
        $response = Response::make(File::get($result[0]->avatar_url));
        $response->header('Content-Type', 'image/png');
        return $response;

    }
    public function getImageCover($id){
        $result=$this->Users->getPathImageCover($id);
        $response = Response::make(File::get($result[0]->img_cover_url));
        $response->header('Content-Type', 'image/png');
        return $response;
    }


    public function updateAvatar($id){
        $result=$this->Users->updateAvatar($id);
        
        if($result['error']==false){
            return Response::json(array('error'=>false, 'message'=>$result['message']), 201);
        }else{
            return Response::json(array('error'=>true, 'message'=>$result['message']), 200);
        }
    }

    public function createReceipt(){
        $result = $this->Users->createReceipt();
        var_dump($result);
    }


    public function login(){
        $result=$this->Users->login();
        if($result['error']==false){
            return Response::json(array('error'=>false, 'message'=>$result['message']), 201);
        }else{
            return Response::json(array('error'=>true, 'message'=>$result['message']), 200);
        }
    }

    public function saveImage(){
        $result=$this->Users->saveImage();
        
        if($result['error']==false){
            return Response::json(array('error'=>false, 'message'=>$result['message']), 201);
        }else{
            return Response::json(array('error'=>true, 'message'=>$result['message']), 200);
        }


    }
}
