@extends('adminlte::page')
@section('title', 'Edit - WineHouse')

@section('content_header')
    <h1>Edit - WineHouse</h1>
@stop

@section('content')
    <div class="containerMaster">
        <div class="wrapper col-md-4">
            <h3>Editar</h3>
            <form role="form" method="POST">
                @csrf
                <div class="form-group">
                    <label for="id">Id:</label>
                    <input value="{{$drink->id}}" readonly type="text" class="form-control" id="id" name="id" placeholder="id">
                </div>

                <div class="form-group">
                    <label for="product">Produto:</label>
                    <input value="{{$drink->product}}" required type="text" class="form-control" id="product" name="product" placeholder="Produto">
                </div>
    
                <div class="form-group">
                    <label for="price">Preço:</label>
                    <input value="{{$drink->price}}" required type="text" class="form-control" id="price" name="price" placeholder="Preço">
                </div>

                <input type="submit" class="btn btn-primary" value="Editar">
            </form>
        </div>
    </div>
@stop

@section('css')
    <link rel="stylesheet" href="../../public/css/style.css">
@stop