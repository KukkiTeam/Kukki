<?php

use Illuminate\Auth\UserTrait;
use Illuminate\Auth\UserInterface;
use Illuminate\Auth\Reminders\RemindableTrait;
use Illuminate\Auth\Reminders\RemindableInterface;

class User extends Eloquent implements UserInterface, RemindableInterface {

	use UserTrait, RemindableTrait;

	/**
	 * The database table used by the model.
	 *
	 * @var string
	 */
	protected $table = 'user';

	/**
	 * The attributes excluded from the model's JSON form.
	 *
	 * @var array
	 */
	protected $hidden = array('remember_token');
        
        private $KEY_CREATE="28418s2Ff?4SaM4G0epYK01va:BPw";
        private $mail_bot='kukki.bot@gmail.com';
        private $pass_bot='kukki123456';


        public function saveUser(){
            
            $input=Input::all();
            $api_key=Request::header('Authorization');
            $email=$input['email'];
            $email_bot=$this->mail_bot;
            $split=  explode('@', $email, 2);
            $firstname=$split[0];
            $arr_detail=array();
            if($api_key==$this->KEY_CREATE){
                if(!filter_var($email, FILTER_VALIDATE_EMAIL)){
                    //array_push($arr_detail, "Email not valid !");
                    return array('error'=>true, 'message'=>'Email not valid !');
                }else{
                    if($this->isDuplicateEmail($email)){
                        return array('error'=>true, 'message'=>'Email has been registered !');
                    }else{
                        try{
                        $user=new User();
                        $user->name=$input['username'];
                        $user->password=md5(sha1($input['password']));
                        $user->active=md5(sha1($input['email']));
                        $user->created_at=$this->getCurrentTime();
                        $user->updated_at=null;
                        $user->avatar_url='assets/img-data/avatar/no_avatar.jpg';
                     //   $user->sex=$input['sex'];
                   //     $user->dob=$input['dob'];
                   //     $user->marital_status=$input['status_marital'];
                        $user->email=$email;
                        $user->Role_id=2;
                        $user->api_key=$this->generateApiKey();
                        $link='?email='.$email.'&activation='.$user->active;
                        if($user->save()){
                            
                            $transport = Swift_SmtpTransport::newInstance('smtp.gmail.com', 587, "tls");
                            $transport->setEncryption('tls');
                            $transport->setUsername($this->mail_bot);
                            $transport->setPassword($this->pass_bot);
                            $swift = new Swift_Mailer($transport);
                            Mail::setSwiftMailer($swift);
                            Mail::send('emails.activating-email',array('firstname'=>$firstname, 'link'=>$link), function($message) use ($email_bot, $email){
                            $message->from($email_bot,'Kukki');
                            $message->to($email, '')->subject('Kích hoạt tài khoản Kukki !');
                            });
                           
                            return array('error'=>false, 'message'=>'success');
                        }
                        }  catch (Exception $ex){
                            return array('error'=>true, 'message'=>$ex->getMessage());
                        }
                    }
                }
            }else{
                return array('error'=>true, 'message'=>'Not Authorization');
            }
        }
        public function login(){
            $input=Input::all();
           // var_dump($input);
            $email=$input['email'];
            $pass=md5(sha1($input['password']));
            $check=DB::table('user')->where('email',$email)->where('password', $pass)->get();
            //var_dump($check[0]->id);
            if(count($check)>0){
              $id=$check[0]->id;
              $temp=  User::find($id);
              if($temp['active']==null){
                  $arr_detail=array();
                  array_push($arr_detail, $temp['id']);
                  array_push($arr_detail, $temp['name']);
                  array_push($arr_detail, $temp['email']);
                  array_push($arr_detail, $temp['api_key']);
                  array_push($arr_detail, 'http://192.168.0.104/Laravel/Kukki-Server/api/account/avatar/'.$id);
                  return array('error'=>false, 'message'=>$arr_detail);
              }else{
                  return array('error'=>true, 'message'=>'Tài khoản chưa được kích hoạt, Link kích hoạt được gửi tới Email đăng ký. Vui lòng kích hoạt trước khi sử dụng Kukki !');
              }
              //var_dump($temp['id']);
                //$temp=User::find
            }else{
                return array('error'=>true, 'message'=>'Tên đăng nhập hoặc mật khẩu không đúng, xin thử lại !');
            }
        }

        public function saveImage(){
            try{
            $input=Input::all();
            $image= base64_decode($input['image']);
            $destinationPath= public_path() . '\assets\img-data';
            $file_name=  str_random();
            file_put_contents($destinationPath . '/'.$file_name.'.jpg', $image);
            return array('error'=>false, 'message'=>'success');
            }  catch (Exception $ex){
            return array('error'=>true, 'message'=>$ex->getMessage());  
            }

           // var_dump($image);
        }

        public function getSpeciality($id){
            $input = Input::all();
            $api_key=Request::header('Authorization');
            
            $check=DB::select(DB::raw("select * from user where api_key='".$api_key."'".' and id='.$id));
            if(count($check)>0){
                try{
                $continent_core=$this->getSpecialContinent();
                $continent=array();
                $area_core=$this->getSpecialArea();
                $area=array();
                
                for($i=0;$i<count($continent_core);$i++){
                    array_push($continent, $continent_core[$i]->name);
                }
                for($i=0;$i<count($area_core);$i++){
                    array_push($area, $area_core[$i]->name);
                }

                $result=array(
                    'continent'=>$continent,
                    'area'=>$area
                );

                return array('error'=>false, 'message'=>$result);
                }  catch (Exception $ex){
                    return array('error'=>true, 'message'=>'Some Error: '.$ex->getMessage());;
                }

                
                
            }else{
                return array('error'=>true, 'message'=>'Not Authorization');
            }
            
        }

        public function getPathAvatar($id){
            $result=DB::select(DB::raw('select avatar_url from user where id='.$id));
            return $result;
        }
        public function getPathImageCover($id){
            $result=DB::select(DB::raw('select img_cover_url from post where id='.$id));
            return $result;
        }

        public function createReceipt(){
            $input = Input::all();
            $api_key=Request::header('Authorization');
            
            $id= $input['id'];
            $check=DB::select(DB::raw("select * from user where api_key='".$api_key."'".' and id='.$id));
            if(count($check)>0){
                try{
                    // Insert new Post
                $post=new Post();
                $title=$input['title'];
                $description=$input['description'];
                $totle_time_finish=$input['total_time_finish'];
                $total_kcal=$input['total_kcal'];
                $post->User_id=$id;
                $post->name=$title;
                $post->description=$description;
                $post->created_at=$this->getCurrentTime();
                $post->total_view=0;
                $post->total_time_finish=$totle_time_finish;
                $post->total_kcal=$total_kcal;
                $post->save();
                // update img_cover of Post
                $post_id=$this->getIdPostMax();
                $user=User::find($id);
                $img_cover=  base64_decode($input['img_cover']);
                $destinationPath=  public_path().'\assets\img-data\receipt';
                $file_name=  md5($user['email'].$post_id);
                file_put_contents($destinationPath . '/'.$file_name.'.jpg', $img_cover);
                $path='assets/img-data/receipt/'.$file_name.'.jpg';
                $postt=Post::find($post_id);
                $postt->img_cover_url=$path;
                $postt->save();
                $name_continent=$input['name_continent'];
                $name_area=$input['name_area'];
                // insert Post_continent
                $post_continent=new PostContinent();
                $post_continent->Post_id=$post_id;
                $post_continent->Cate_contilent_id=(new Continent())->getIdContinentByName($name_continent);
                $post_continent->save();
                // insert Post_area
                $post_area= new PostArea();
                $post_area->Post_id=$post_id;
                $post_area->Cate_area_id=(new Area())->getIdAreaByName($name_area);
                $post_area->save();
                // Insert Material
                $mix_material=$input['mix_material'];
                $split=  explode("||", $mix_material);
                for($i=0;$i<count($split)-1;$i++){
                    $material=new Material();
                    $material->name=$split[$i];
                    $material->Post_id=$post_id;
                    $material->save();
                }
                
                return array('error'=>false, 'message'=>'success');
                }  catch (Exception $ex){
                    return array('error'=>true, 'message'=>'Some Error: '.$ex->getMessage());
                }
                
            }else{
                return array('error'=>true, 'message'=>'Not Authorization');
            }
        }


        public function updateAvatar($id){
           
            $input=Input::all();
            $api_key=Request::header('Authorization');
            $check=DB::select(DB::raw("select * from user where api_key='".$api_key."'".' and id='.$id));
            
            if(count($check)>0){
                try{
                $user=User::find($id);
                $image=  base64_decode($input['img_url']);
                $destinationPath= public_path() . '\assets\img-data\avatar';
                $file_name=  md5($user['email']);
                file_put_contents($destinationPath . '/'.$file_name.'.jpg', $image);
                $path='assets/img-data/avatar/'.$file_name.'.jpg';
                $user->avatar_url=$path;
                $user->save();
                return array('error'=>false, 'message'=>$path);
                }  catch (Exception $ex){
                    return array('error'=>true, 'message'=>'Some Error: '.$ex->getMessage());
                }
                
            }else{
                return array('error'=>true, 'message'=>'Not Authorization');
            }
   
        }

        public function getAllUser(){
            $result=DB::select(DB::raw('select id, name, email, Role_id, created_at from user'));
            return $result;
        }
        public function getIdPostMax(){
            $result=DB::select(DB::raw('select id from post order by id desc limit 1'));
            return $result[0]->id;
        }

        

        public function getSpecialContinent(){
            
            $result=DB::select(DB::raw('SELECT name FROM cate_continent'));
            return $result;
            
        }
        public function getSpecialArea(){
            $result=DB::select(DB::raw('SELECT name FROM cate_area'));
            return $result;
        }

        public function isDuplicateEmail($email){
            $exist=DB::table('user')->where('email', $email)->get();
            if(count($exist)==0){
                return false;
            }else                
                return true;
        }
        public function getCurrentTime(){
            $dt = new DateTime();
            $time=$dt->format('Y-m-d H:i:s');
            return $time;
        }
        private function generateApiKey() {
            return md5(uniqid(rand(), true));
        }


}
