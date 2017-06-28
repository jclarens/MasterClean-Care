<?php

namespace App\Http\Controllers;

use App\WalletTransaction;
use Illuminate\Http\Request;

class WalletTransactionController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return WalletTransaction::all();
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $data = $request->all();

        $walletTransaction = WalletTransaction::create($data);

        return response()->json($walletTransaction, 201);
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\WalletTransaction  $walletTransaction
     * @return \Illuminate\Http\Response
     */
    public function show(WalletTransaction $walletTransaction)
    {
        return $walletTransaction;
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  \App\WalletTransaction  $walletTransaction
     * @return \Illuminate\Http\Response
     */
    public function edit(WalletTransaction $walletTransaction)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\WalletTransaction  $walletTransaction
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, WalletTransaction $walletTransaction)
    {
        $data = $request->all();

        if (array_key_exists('userId', $data)) {
            $walletTransaction->userId = $data['userId'];
        }
        if (array_key_exists('walletId', $data)) {
            $walletTransaction->walletId = $data['walletId'];
        }
        if (array_key_exists('trcType', $data)) {
            $walletTransaction->trcType = $data['trcType'];
        }
        if (array_key_exists('trcTime', $data)) {
            $walletTransaction->trcTime = $data['trcTime'];
        }
        if (array_key_exists('walletCode', $data)) {
            $walletTransaction->walletCode = $data['walletCode'];
        }

        $walletTransaction->save();

        return response()->json($walletTransaction, 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\WalletTransaction  $walletTransaction
     * @return \Illuminate\Http\Response
     */
    public function destroy(WalletTransaction $walletTransaction)
    {
        $walletTransaction->delete();

        return response()->json('Deleted', 200);
    }
}
