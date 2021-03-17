@extends('adminlte::page')

@section('title', 'Brands')

@section('content')

    <div class="form-style-5">
        <h1 class="m-b-20 fs-25 flex-c">Nova marca?</h1>
        <form action="" method="post">
            @csrf
            <input type="text" name="descBrand" placeholder="Nome" required>
            <input type="submit" value="Cadastrar"/>

        </form>
    </div>

    @if($brands == null)
        <h1 class="flex-c m-t-100 fs-30" style="color: lightgray">Nada registrado :/</h1>
    @else
        <div class="container-master brands m-l-30 m-r-30">
            <div class="container-hyper">
                <div class="table">
                
                    <div class="row header">
                        <div class="cell">Marca</div>
                        <div class="cell">Path</div>
                        <div class="cell">Id</div>
                    </div>
            
                    @foreach($brands as $key => $brand)
                        <div class="row brand">
                            <div class="cell" data-title="Marca">{{$brand['desc']}}</div>
                            <div class="cell" data-title="Path">{{$brand['url']}}</div>
                            <div class="cell" data-title="Id">{{$key}}</div>
                        </div>
                    @endforeach
                </div>
            </div>
        </div>

    @endif
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

@stop
