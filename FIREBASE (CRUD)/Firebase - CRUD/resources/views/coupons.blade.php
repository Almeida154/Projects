@extends('adminlte::page')

@section('title', 'Coupons')

@section('content')

    <div class="form-style-5">
        <h1 class="m-b-20 fs-25 flex-c">Novo cupom?</h1>
        <form action="" method="post">
            @csrf
            <input type="text" name="name" placeholder="Nome" required>
            <input type="text" name="discount" placeholder="Preço do desconto" require>
            <input type="text" name="minPrice" placeholder="Preço mínimo (Opcional)">
            <input type="submit" value="Cadastrar"/>

        </form>
    </div>

    @if($coupons == null)
        <h1 class="flex-c m-t-100 fs-30" style="color: lightgray">Nada registrado :/</h1>
    @else
        <div class="container-master coupons m-l-30 m-r-30">
            <div class="container-hyper">
                <div class="table">
                
                    <div class="row header">
                        <div class="cell">Nome</div>
                        <div class="cell">Desconto</div>
                        <div class="cell">Mínimo (R$)</div>
                        <div class="cell">Id</div>
                    </div>
            
                    @foreach($coupons as $key => $coupon)
                        <div class="row coupon">
                            <div class="cell" data-title="Nome">{{$coupon['name']}}</div>
                            <div class="cell" data-title="Desconto">R$ {{sprintf("%01.2f", $coupon['discount'])}}</div>
                            @if($coupon['minPrice'] != 'none')
                                <div class="cell" data-title="Mínimo (R$)">R$ {{sprintf("%01.2f", $coupon['minPrice'])}}</div> 
                            @else
                                <div class="cell" data-title="Mínimo (R$)">None :)</div>
                            @endif
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
