@extends('adminlte::page')
@section('title', 'Add - WineHouse')

@section('content_header')
    <h1>Add - WineHouse</h1>
@stop

@section('content')
    <div class="containerMaster">
        <div class="wrapper col-md-4">
            <h3>Cadastre uma nova bebida</h3>
            <form role="form" method="POST">
                @csrf
                <div class="form-group">
                    <label for="product">Produto:</label>
                    <input required type="text" class="form-control" id="product" name="product" placeholder="Produto">
                </div>
    
                <div class="form-group">
                    <label for="price">Preço:</label>
                    <input required type="text" class="form-control" id="price" name="price" placeholder="Preço">
                </div>

                <input type="submit" class="btn btn-primary">
            </form>
        </div>
    </div>
@stop

@section('css')
    <link rel="stylesheet" href="../public/css/style.css">
@stop