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




            <div class="row">
                <div class="col-lg-12">
                    <!--Simple table example -->
                    
                    <div class="panel panel-default">
                        <div class="panel-heading">
                             Total Recipes
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>User Name</th>
                                            <th>Recipe Name</th>
                                            <th>Description</th>
                                            <th>Image</th>
                                            <th>Total View</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        
                                        @for($i=0;$i<$table_size;$i++)
                                            
                                            <tr>
                                                <td>{{$table[$i]->artis_name}}</td>
                                                <td><a href="#">{{$table[$i]->recipe_name}}</a></td>
                                                <td>{{$table[$i]->description}}</td>
                                                <td><img src="{{Asset('assets/img-data/receipt/0c7c771f85f0f3633fe4f6e5db1525b9.jpg')}}" alt="" /></td>
                                              <!--  <td><img src="{{Asset($table[$i]->img_cover_url)}}" alt="" /></td> -->
                                                <td>{{$table[$i]->total_view}}</td>
                                            </tr>
                                        
                                        @endfor
                                    </tbody>
                                </table>
                            </div>
                            
                        </div>
                    </div>
                    
                     <!--End simple table example -->

                </div>
        

            </div>
@endsection