<!-- navbar side -->
        <nav class="navbar-default navbar-static-side" role="navigation">
            <!-- sidebar-collapse -->
            <div class="sidebar-collapse">
                <!-- side-menu -->
                <ul class="nav" id="side-menu">
                    <li>
                        <!-- user image section-->
                        <div class="user-section">
                            <div class="user-section-inner">
                                <img src="{{Asset('assets/img/user.jpg')}}" alt="">
                            </div>
                            <div class="user-info">
                                <div><strong>{{$admin_name}}</strong></div>
                                <div class="user-text-online">
                                    <span class="user-circle-online btn btn-success btn-circle "></span>&nbsp;Online
                                </div>
                            </div>
                        </div>
                        <!--end user image section-->
                    </li>
                     @if($side_active==1)
                     <li class="selected">
                        <a href="{{Asset('site/index')}}"><i class="fa fa-dashboard fa-fw"></i>Dashboard</a>
                    </li>
                    @else
                    <li>
                        <a href="{{Asset('site/index')}}"><i class="fa fa-dashboard fa-fw"></i>Dashboard</a>
                    </li>
                     @endif
                    
                     @if($side_active==2)
                     <li class="selected">
                        <a href="{{Asset('site/set-admin')}}"><i class="fa fa-flag fa-fw"></i>Set Admin</a>
                    </li>
                    @else
                    <li>
                        <a href="{{Asset('site/set-admin')}}"><i class="fa fa-flag fa-fw"></i>Set Admin</a>
                    </li>
                     @endif
                     
                     
                     
                     
                     
                    
                    <li>
                        <a href="#"><i class="fa fa-ban fa-fw"></i>Banned member</a>
                    </li>
                    
                    <li>
                        <a href="#"><i class="fa fa-list fa-fw"></i>Albums Manager</a>
                    </li>
                    
                    <li>
                        <a href="#"><i class="fa fa-user fa-fw"></i>Members Manager</a>
                    </li>
                    
                    <li>
                        <a href="#"><i class="fa fa-cutlery fa-fw"></i>Recipes Manager</a>
                    </li>
                </ul>
                <!-- end side-menu -->
            </div>
            <!-- end sidebar-collapse -->
        </nav>
        <!-- end navbar side -->