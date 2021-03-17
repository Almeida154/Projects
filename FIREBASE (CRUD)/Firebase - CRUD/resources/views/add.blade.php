@extends('adminlte::page')

@section('title', 'Add')

@section('content_header')
    <h1>Add</h1>
@stop

@section('content')
    <div class="form-style-5 m-t-50">
        <h1 class="m-b-20 fs-25 flex-c">Cadastrar nova bebida</h1>
        <form method="POST" autocomplete="off">
            @csrf
            <input autocomplete="nope" type="text" name="name" placeholder="Bebida" required>
            <input type="text" name="price" placeholder="Preço" required>
            <select name="brand">
                <option value="null">Marca</option>
                @foreach ($brands as $brand)
                    <option value="{{$brand['id']}}">{{$brand['desc']}}</option>
                @endforeach
            </select>
            <input type="submit" value="Cadastrar"/>
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