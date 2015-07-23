<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <!-- Core CSS - Include with every page -->
    <link href="{{Asset('assets/plugins/bootstrap/bootstrap.css')}}" rel="stylesheet" />
    <link href="{{Asset('assets/font-awesome/css/font-awesome.css')}}" rel="stylesheet" />
    <link href="{{Asset('assets/plugins/pace/pace-theme-big-counter.css')}}" rel="stylesheet" />
   <link href="{{Asset('assets/css/style.css')}}" rel="stylesheet" />
      <link href="{{Asset('assets/css/main-style.css')}}" rel="stylesheet" />

</head>

<body class="body-Login-back">

    <div class="container">
                    
        <div class="row">
            
                    
            <div class="col-md-4 col-md-offset-4 text-center logo-margin ">
              <img src="{{Asset('assets/img/logo4.png')}}" alt=""/>
                </div>
            <div class="col-md-4 col-md-offset-4"></div>
                    <div class="col-md-4 col-md-offset-4">
                        @if(count($errors)!=0)
                    <div class="alert alert-danger alert-dismissible" role="alert">
                      <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                      @foreach($errors as $value)
                      <p>{{$value}}</p>
                      @endforeach
                    </div>
                    @endif
                    </div>
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">                  
                    <div class="panel-heading">
                        <h3 class="panel-title">Please Sign In</h3>
                    </div>
                    <div class="panel-body">
                        <form role="form" action="{{Asset('site/login')}}" method="post">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="E-mail" name="email" type="email" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Password" name="password" type="password" value="">
                                </div>
                                <!-- Change this to a button or input when using this as a form -->
                                
                                <button type="submit" class="btn btn-lg btn-success btn-block">Login</button>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

     <!-- Core Scripts - Include with every page -->
    <script src="{{Asset('assets/plugins/jquery-1.10.2.js')}}"></script>
    <script src="{{Asset('assets/plugins/bootstrap/bootstrap.min.js')}}"></script>
    <script src="{{Asset('assets/plugins/metisMenu/jquery.metisMenu.js')}}"></script>

</body>

</html>
