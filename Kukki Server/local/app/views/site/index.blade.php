@extends('layout/main')

@section('title')
HomePage
@endsection

@section('content')

<div class="row">
                <!-- Page Header -->
                <div class="col-lg-12">
                    <h1 class="page-header">Dashboard</h1>
                </div>
                <!--End Page Header -->
            </div>

            <div class="row">
                <!-- Welcome -->
                <div class="col-lg-12">
                    <div class="alert alert-info">
                        <i class="fa fa-folder-open"></i><b>&nbsp;Hello ! </b>Welcome Back <b>{{$admin_name}}</b>
 
                    </div>
                </div>
                <!--end  Welcome -->
            </div>


            <div class="row">
                <!--quick info section -->
                <div class="col-lg-3">
                    <div class="alert alert-danger text-center">
                        <i class="fa fa-cutlery fa-3x"></i>&nbsp;<a href="{{Asset('site/recipes')}}"><b>1000</b> Total Recipes</a>

                    </div>
                </div>
                <div class="col-lg-3">
                    <div class="alert alert-success text-center">
                        <i class="fa  fa-user fa-3x"></i>&nbsp;<a href="#"><b>1000</b> Total Users </a>
                    </div>
                </div>
                <div class="col-lg-3">
                    <div class="alert alert-info text-center">
                        <i class="fa fa-list fa-3x"></i>&nbsp;<a href="#"><b>1,900</b> Total Albums</a>

                    </div>
                </div>

                <!--end quick info section -->
            </div>



//////
            <div class="row">
                <div class="col-lg-8">
                    <!--Simple table example -->
                    
                    <div class="panel panel-default">
                        <div class="panel-heading">
                             Total users
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Name</th>
                                            <th>Email</th>
                                            <th>Role</th>
                                            <th>Register Date</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        
                                        @for($i=0;$i<$table_size;$i++)
                                            
                                            <tr>
                                                <td>{{$table[$i]->id}}</td>
                                                <td>{{$table[$i]->name}}</td>
                                                <td>{{$table[$i]->email}}</td>
                                                <td>{{($table[$i]->Role_id==1)?'Admin':'Member'}}</td>
                                                <td>{{$table[$i]->created_at}}</td>
                                            </tr>
                                        
                                        @endfor
                                    </tbody>
                                </table>
                            </div>
                            
                        </div>
                    </div>
                    
                     <!--End simple table example -->

                </div>

                <div class="col-lg-4">
                    <div class="panel panel-primary text-center no-boder">
                        <div class="panel-body yellow">
                            <i class="fa fa-user fa-3x"></i>
                            <h3>20,741 </h3>
                        </div>
                        <div class="panel-footer">
                            <span class="panel-eyecandy-title"><a href="#">Daily Registered Users</a>
                            </span>
                        </div>
                    </div>
                    <div class="panel panel-primary text-center no-boder">
                        <div class="panel-body red">
                            <i class="fa fa-times fa-3x"></i>
                            <h3>2,060 </h3>
                        </div>
                        <div class="panel-footer">
                            <span class="panel-eyecandy-title"><a href="#">Overdue account activation</a>
                            </span>
                        </div>
                    </div>
                    <div class="panel panel-primary text-center no-boder">
                        <div class="panel-body green">
                            <i class="fa fa fa-floppy-o fa-3x"></i>
                            <h3>Empty</h3>
                        </div>
                        <div class="panel-footer">
                            <span class="panel-eyecandy-title">Empty
                            </span>
                        </div>
                    </div>
                    <div class="panel panel-primary text-center no-boder">
                        <div class="panel-body green">
                            <i class="fa fa-thumbs-up fa-3x"></i>
                            <h3>Empty </h3>
                        </div>
                        <div class="panel-footer">
                            <span class="panel-eyecandy-title">Empty
                            </span>
                        </div>
                    </div>
                </div>

            </div>
@endsection