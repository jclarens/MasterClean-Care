<?php

namespace App\Http\Controllers;

use App\UserWallet;
use Illuminate\Http\Request;

class UserWalletController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return UserWallet::all();
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

        $userWallet = UserWallet::create($data);

        return response()->json($userWallet, 201);
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\UserWallet  $userWallet
     * @return \Illuminate\Http\Response
     */
    public function show(UserWallet $userWallet)
    {
        return $userWallet;
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  \App\UserWallet  $userWallet
     * @return \Illuminate\Http\Response
     */
    public function edit(UserWallet $userWallet)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\UserWallet  $userWallet
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, UserWallet $userWallet)
    {
        $data = $request->all();

        if (array_key_exists('user_id', $data)) {
            $userWallet->user_id = $data['user_id'];
        }
        if (array_key_exists('wallet_id', $data)) {
            $userWallet->wallet_id = $data['wallet_id'];
        }

        $userWallet->save();

        return response()->json($userWallet, 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\UserWallet  $userWallet
     * @return \Illuminate\Http\Response
     */
    public function destroy(UserWallet $userWallet)
    {
        $userWallet->delete();

        return response()->json('Deleted', 200);
    }
}