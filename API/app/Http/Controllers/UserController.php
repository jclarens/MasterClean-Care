<?php

namespace App\Http\Controllers;

use App\User;
use App\UserWallet;
use App\UserLanguage;
use App\UserJob;
use App\UserWorkTime;
use App\UserDocument;
use App\UserAdditionalInfo;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
// use Exception;
// use DB;

class UserController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return User::all();
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
        
        $email = $data['email'];
        
        $exists = User::where('email', $email)->get()->first();
        if (!is_null($exists)) {
            return response()->json([
                'error' => "A user with the email $email already exists!"
            ]);
        }

        // Insert User
        try {
            DB::beginTransaction();
            
            $user = User::create($data);
            // $user = new User;
            // $user->name = $data['name'];
            // $user->email = $data['email'];
            // $user->password = Hash::make($data['password']);
            // $user->gender = $data['gender'];
            // $user->bornPlace = $data['bornPlace'];
            // $user->bornDate = $data['bornDate'];
            // $user->phone = $data['phone'];
            // $user->province = $data['province'];
            // $user->city = $data['city'];
            // $user->address = $data['address'];
            // $user->location = $data['location'];
            // $user->religion = $data['religion'];
            // $user->race = $data['race'];
            // $user->userType = $data['userType'];
            // $user->profileImgName = $data['profileImgName'];
            // $user->profileImgPath = $data['profileImgPath'];
            // $user->status = $data['status'];

            // $userWallet = new UserWallet;
            // $userWallet->amt = 0; 
            // $user->userWallet()->save($userWallet);

            // $userLangugage = new UserLanguage;

            // $user->userWallet()->save(new UserWallet(array('amt' => 0)));

            DB::commit();

            return response()->json($user, 201);
        }
        catch(Exception $e) {
            DB::rollBack();
            return response()->json([ 'message' => $e->getMessage(), 
                                      'status' => 400 ]);
        }
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\User $user
     * @return \Illuminate\Http\Response
     */
    public function show(User $user)
    {
        return $user;
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  \App\User  $user
     * @return \Illuminate\Http\Response
     */
    public function edit(User $user)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\User  $user
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, User $user)
    {
        $data = $request->all();

        if (array_key_exists('name', $data)) {
            $user->name = $data['name'];
        }
        if (array_key_exists('email', $data)) {
            $email = $data['email'];
            
            $exists = User::where('email', $email)->where('id', '!=', $user->id)->get()->first();
            if (!is_null($exists)) {
                return response()->json([
                    'error' => "A user with the email $email already exists!"
                ]);
            }
            $user->email = $email;
        }
        if (array_key_exists('password', $data)) {
            $user->password = $data['password'];
        }
        if (array_key_exists('gender', $data)) {
            $user->gender = $data['gender'];
        }
        if (array_key_exists('bornPlace', $data)) {
            $user->bornPlace = $data['bornPlace'];
        }
        if (array_key_exists('bornDate', $data)) {
            $user->bornDate = $data['bornDate'];
        }
        if (array_key_exists('phone', $data)) {
            $user->phone = $data['phone'];
        }
        if (array_key_exists('province', $data)) {
            $user->city = $data['province'];
        }
        if (array_key_exists('city', $data)) {
            $user->city = $data['city'];
        }
        if (array_key_exists('address', $data)) {
            $user->address = $data['address'];
        }
        if (array_key_exists('location', $data)) {
            $user->location = $data['location'];
        }
        if (array_key_exists('religion', $data)) {
            $user->religion = $data['religion'];
        }
        if (array_key_exists('race', $data)) {
            $user->race = $data['race'];
        }
        if (array_key_exists('userType', $data)) {
            $user->userType = $data['userType'];
        }
        if (array_key_exists('profileImgName', $data)) {
            $user->profileImgName = $data['profileImgName'];
        }
        if (array_key_exists('profileImgPath', $data)) {
            $user->profileImgPath = $data['profileImgPath'];
        }
        if (array_key_exists('status', $data)) {
            $user->status = $data['status'];
        }

        $user->save();

        return response()->json($user, 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\User  $user
     * @return \Illuminate\Http\Response
     */
    public function destroy($user)
    {
        $user->delete();

        return response()->json('Deleted', 200);
    }

    /**
     * Search the specified resource from storage by parameter.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\User  $user
     * @param  Parameter  $param
     * @param  Text  $text
     * @return \Illuminate\Http\Response
     */
    public function searchByParam(Request $request, User $user, $param, $text)
    {
        return $user
            ->where($param,
                'like',
                '%'.$text.'%')
            ->get();
    }

    /**
     * Search the specified resource from storage by parameter.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\User  $user
     * @param  Parameter  $param
     * @param  Text  $text
     * @return \Illuminate\Http\Response
     */
    public function search(Request $request, User $user, $text)
    {
        return $user
            ->where('name',
                'like',
                '%'.$text.'%')
            ->orWhere('city',
                'like',
                '%'.$text.'%')
            ->get();
    }
}
