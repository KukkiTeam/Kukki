<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

class Continent extends Eloquent{
    protected $table="cate_continent";
    protected $created_at=false;
    protected $updated_at=false;
    
    public function getIdContinentByName($name){
        $result=DB::select(DB::raw("select id from cate_continent where name like '".$name."'"));
        return $result[0]->id;
    }
}