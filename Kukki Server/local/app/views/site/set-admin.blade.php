@extends('layout/main')

@section('title')
Set Admin
@endsection

@section('content')
            <div class="row">
                <!-- Page Header -->
                <div class="col-lg-12">
                        
                    <h1 class="page-header">Set Admin</h1>
                    @if(count($errors)!=0)
                    <div class="alert alert-success alert-dismissible" role="alert">
                      <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                      @foreach($errors as $value)
                      <p>{{$value}}</p>
                      @endforeach
                    </div>
                    @endif
                </div>
                <!--End Page Header -->
            </div>

            


            

            <div class="row">
                <div class="col-lg-12">
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
                                            <th>Email</th>
                                            <th>Name</th>
                                            <th>Role</th>
                                            <th>Register Date</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        
                                        @for($i=0;$i<$table_size;$i++)
                                        
                                            <tr>
                                                <td>{{$table[$i]->id}}</td>
                                                <td><a href="#myModal" data-toggle="modal" data-email-id="{{$table[$i]->id}}" data-email-name="{{$table[$i]->email}}" data-email-role="{{$table[$i]->Role_id}}" data-backdrop="false" class="open-EmailDialog">{{$table[$i]->email}}</a></td>
                                                    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                                        <div class="modal-dialog">
                                                          <div class="modal-content">
                                                            <div class="modal-header">
                                                              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                                              <h4 class="modal-title" id="myModalLabel">Email Detials</h4>
                                                            </div>
                                                            <div class="modal-body">
                                                                <div class="form-horizontal">
                                                                    <form method="post" action="{{Asset('site/set-admin')}}">
                                                                    <div class="form-group row">
                                                                        <div class="col-md-10">
                                                                            <label class="col-md-3 control-label">ID</label>
                                                                            <div class="col-md-3">
                                                                                <input name="input_id" class="form-control" id="input_id" placeholder="" readonly="readonly">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="form-group row">
                                                                         <div class="col-md-10">
                                                                            <label class="col-md-3 control-label">Email</label>
                                                                            <div class="col-md-7">
                                                                                <input name="input_email" class="form-control" id="input_email" placeholder="" readonly="readonly">
                                                                            </div>
                                                                        </div>        
                                                                    </div>
                                                                    <div class="form-group row">
                                                                        
                                                                            <div class="col-md-10">
                                                                            <label class="col-md-3 control-label">Set Role</label>
                                                                            <div class="col-md-4">
                                                                                <select name="role" class="form-control">
                                                                                    <option value="0" selected="true">Select</option>
                                                                                    <option value="2">Member</option>
                                                                                    <option value="1">Admin</option>
                                                                                </select>
                                                                            </div>
                                                                            <div class="col-md-3 col-md-offset-1">
                                                                                <button type="submit" class="btn btn-success" onclick="return confirm('This Email will be changed Role ?');">Set</button>
                                                                            </div>
                                                                            </div>
                                                                                   
                                                                    </div>
                                                                    </form>
                                                                </div>
                                                            </div>
                                                            <div class="modal-footer">
                                                              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                            </div>
                                                          </div>
                                                        </div>
                                                  </div>
                                                <script>
                                                    $('#my_modal').on('show.bs.modal', function(e) {
                                                        var id = $(e.relatedTarget).data('email-id');
                                                        $(e.currentTarget).find('input[name="id"]').val(id);
                                                    });
                                                </script>
                                                
                                                <td>{{$table[$i]->name}}</td>
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

                

            </div>
@endsection