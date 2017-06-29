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

        if (array_key_exists('user_id', $data)) {
            $walletTransaction->user_id = $data['user_id'];
        }
        if (array_key_exists('wallet_id', $data)) {
            $walletTransaction->wallet_id = $data['wallet_id'];
        }
        if (array_key_exists('trc_type', $data)) {
            $walletTransaction->trc_type = $data['trc_type'];
        }
        if (array_key_exists('trc_time', $data)) {
            $walletTransaction->trc_time = $data['trc_time'];
        }
        if (array_key_exists('wallet_code', $data)) {
            $walletTransaction->wallet_code = $data['wallet_code'];
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
