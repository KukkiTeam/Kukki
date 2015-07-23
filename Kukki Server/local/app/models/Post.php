<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
class Post extends Eloquent{
    protected $table="post";
    protected $created_at=false;
    protected $updated_at=false;
    
    public function getRecipes(){
          $result=DB::select(DB::raw('select user.name as artis_name, post.name as recipe_name, post.description, post.img_cover_url, post.total_view from user,post where user.id = post.user_id'));
          return $result; 
    }
}
