@extends('adminlte::page')

@section('title', 'Home - WineHouse')

@section('content_header')
    <h1>Dashboard - WineHouse</h1>
@stop

@section('content')
    <div class="containerMaster">
        <div class="wrapper col-md-8">
            <h3>Menu</h3>
            <div class="box">
                <div class="box-body table-responsive no-padding">
                    <table class="table table-hover">
                        <tbody>
                            <tr>
                                <th>Produto</th>
                                <th>Preço</th>
                                <th>Opções</th>
                            </tr>

                            @foreach($drinks as $drink)
                                <tr>
                                    <td><p>{{$drink->product}}</p></td>
                                    <td><p>R${{$drink->price}}</td>
                                    <td class="line">
                                        <a class="edit btn btn-primary" href="edit/{{$drink->id}}">Editar</a>
                                        <a class="delete btn btn-primary" href="delete/{{$drink->id}}"> Excluir</a>
                                    </td>
                                </tr>
                            @endforeach
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
@stop

@section('css')
    <link rel="stylesheet" href="../public/css/style.css">
@stop