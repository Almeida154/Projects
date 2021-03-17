@extends('adminlte::page')

@section('title', 'Menu')

@section('content_header')
    <h1 class="lixo">Menu</h1>
@stop

@section('content')
    <p>Welcome to this beautiful admin panel.</p>

    <div class="container-master">

    </div>

@stop

@section('css')
    <link rel="stylesheet" href="/css/style.css">

    <link rel="stylesheet" href="/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="/vendor/animate/animate.css">
    <link rel="stylesheet" href="/vendor/select2/select2.min.css">
    <link rel="stylesheet" href="/vendor/perfect-scrollbar/perfect-scrollbar.css">
    <link rel="stylesheet" href="/css/util.css">
	<link rel="stylesheet" href="/css/main.css">
@stop

@section('js')

    <script src="https://code.jquery.com/jquery-3.5.1.js"></script>

    <script>

        var refresh = (function verify(e){
            $.ajax({
                type: 'GET',
                url: "{{route('table')}}",
                data: "{{route('table')}}",
                cache: false,
                success: function(data){
                    //alert('{{route('table')}} funcionou em')
                    //console.log(data)
                    $('.container-master').html(data)
                },
                error: function(){
                    //alert('{{route('table')}} deu n kk')
                    //console.log(e)
                }
            })
            return verify
        })

        $(document).ready(function(){
            refresh()
            setInterval(refresh, (1000 * 10))
        })

    </script>

@stop