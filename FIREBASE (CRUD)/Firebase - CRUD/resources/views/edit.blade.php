@extends('adminlte::page')

@section('title', 'Edit')

@section('content_header')
    <h1>Edit</h1>
@stop

@section('content')
    <div class="form-style-5 m-t-40">
        <h1 class="m-b-20 fs-25 flex-c">Edite a bebida</h1>
        <form method="POST" autocomplete="off">
            @csrf
            <label for="id">Id:</label>
            <input value="{{$key}}" value="asjdnjasnd" readonly type="text" name="key" placeholder="Bebida" required>
            <label for="id">Bebida:</label>
            <input value="{{$drink['name']}}" autocomplete="nope" type="text" name="name" placeholder="Bebida" required>
            <label for="id">Preço:</label>
            <input value="{{$drink['price']}}" type="text" name="price" placeholder="Preço" required>
            <input type="submit" value="Editar"/>
        </form>
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
    <script src="/js/script.js"></script>
    <script src="/vendor/select2/select2.min.js"></script>
@stop