<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
?>
<!DOCTYPE html>
<html>
<head>
    <title>@yield('title')</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Core CSS - Include with every page -->
    <link href="{{Asset('assets/plugins/bootstrap/bootstrap.css')}}" rel="stylesheet" />
    <link href="{{Asset('assets/font-awesome/css/font-awesome.css')}}" rel="stylesheet" />
    <link href="{{Asset('assets/plugins/pace/pace-theme-big-counter.css')}}" rel="stylesheet" />
    <link href="{{Asset('assets/css/style.css')}}" rel="stylesheet" />
    <link href="{{Asset('assets/css/main-style.css')}}" rel="stylesheet" />
    

    <!-- Page-Level CSS -->
    <link href="{{Asset('assets/plugins/morris/morris-0.4.3.min.css')}}" rel="stylesheet" />
    <link href="{{Asset('assets/js/dataTables/dataTables.bootstrap.css')}}" rel="stylesheet" />
        
</head>
<body>

    <div id="wrapper">
        @include('layout.header')
        @include('layout.side-bar')
        <div id='page-wrapper'>
            <section id="content">
            @yield('content')
            </section>
        </div>
    </div>
    
    <!-- Core Scripts - Include with every page -->
    <!--
    <script src="{{Asset('assets/plugins/jquery-1.10.2.js')}}"></script>
    <script src="{{Asset('assets/plugins/bootstrap/bootstrap.min.js')}}"></script>
    
    <script src="{{Asset('assets/plugins/pace/pace.js')}}"></script>
    <script src="{{Asset('assets/scripts/siminta.js')}}"></script>
    
    <script src="{{Asset('assets/plugins/morris/raphael-2.1.0.min.js')}}"></script>
    <script src="{{Asset('assets/plugins/morris/morris.js')}}"></script>
    <script src="{{Asset('assets/scripts/dashboard-demo.js')}}"></script>
        <script src="{{Asset('assets/js/dataTables/jquery.dataTables.js')}}"></script>
    <script src="{{Asset('assets/js/dataTables/dataTables.bootstrap.js')}}"></script>
            <script>
            $(document).ready(function () {
                $('#dataTables-example').dataTable();
            });
    </script>
        <script src="{{Asset('assets/js/custom.js')}}"></script>
-->
<script src="{{Asset('assets/js/jquery-1.10.2.js')}}"></script>
      <!-- BOOTSTRAP SCRIPTS -->
    <script src="{{Asset('assets/js/bootstrap.min.js')}}"></script>
    <!-- METISMENU SCRIPTS -->
    <script src="{{Asset('assets/js/jquery.metisMenu.js')}}"></script>
    
     <script src="{{Asset('assets/plugins/pace/pace.js')}}"></script>
    <script src="{{Asset('assets/scripts/siminta.js')}}"></script>
     <!-- DATA TABLE SCRIPTS -->
    <script src="{{Asset('assets/js/dataTables/jquery.dataTables.js')}}"></script>
    <script src="{{Asset('assets/js/dataTables/dataTables.bootstrap.js')}}"></script>
        <script>
            $(document).ready(function () {
                $('#dataTables-example').dataTable();
            });
    </script>
    <script>
    $(document).on("click", ".open-EmailDialog", function (e) {

	e.preventDefault();

	var _self = $(this);

	var email_id = _self.data('email-id');
        var email_name = _self.data('email-name');
        var email_role=_self.data('email-role');
	$("#input_id").val(email_id);
        $("#input_email").val(email_name);
            
	$(_self.attr('href')).modal('show');
});
</script>
    <script src="{{Asset('assets/js/custom.js')}}"></script>
</body>
</html>
